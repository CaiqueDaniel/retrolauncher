package services_test

import (
	"errors"
	"retrolauncher/backend/src/app/settings/internal/services"
	"testing"

	"github.com/godbus/dbus/v5"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
)

// ---------------------------------------------------------------------------
// Mock: DBusConn
// ---------------------------------------------------------------------------

type mockDBusConn struct {
	mock.Mock
}

func (m *mockDBusConn) Object(dest string, path dbus.ObjectPath) dbus.BusObject {
	args := m.Called(dest, path)
	return args.Get(0).(dbus.BusObject)
}

// ---------------------------------------------------------------------------
// Mock: dbus.BusObject
// ---------------------------------------------------------------------------

type mockBusObject struct {
	dbus.BusObject
	mock.Mock
}

func (m *mockBusObject) Call(method string, flags dbus.Flags, args ...interface{}) *dbus.Call {
	callArgs := []interface{}{method, flags}
	callArgs = append(callArgs, args...)
	result := m.Called(callArgs...)
	return result.Get(0).(*dbus.Call)
}

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

const testSession = dbus.ObjectPath("/org/freedesktop/secrets/session/1")

func successCall(body ...interface{}) *dbus.Call {
	return &dbus.Call{Body: body}
}

func errorCall(err error) *dbus.Call {
	return &dbus.Call{Err: err}
}

// ---------------------------------------------------------------------------
// SaveCredentials tests
// ---------------------------------------------------------------------------

func TestSaveCredentials_Success(t *testing.T) {
	conn := &mockDBusConn{}
	collectionObj := &mockBusObject{}

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets/aliases/default")).
		Return(collectionObj)

	itemPath := dbus.ObjectPath("/org/freedesktop/secrets/collection/default/1")
	prompt := dbus.ObjectPath("/")

	collectionObj.On("Call",
		"org.freedesktop.Secret.Collection.CreateItem",
		dbus.Flags(0),
		mock.AnythingOfType("map[string]dbus.Variant"),
		mock.AnythingOfType("services.DBusSecret"),
		true,
	).Return(successCall(itemPath, prompt))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	err := manager.SaveCredentials("api_key", "super-secret-value")

	assert.NoError(t, err)
	conn.AssertExpectations(t)
	collectionObj.AssertExpectations(t)
}

func TestSaveCredentials_DBusError(t *testing.T) {
	conn := &mockDBusConn{}
	collectionObj := &mockBusObject{}

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets/aliases/default")).
		Return(collectionObj)

	dbusErr := errors.New("D-Bus: access denied")

	collectionObj.On("Call",
		"org.freedesktop.Secret.Collection.CreateItem",
		dbus.Flags(0),
		mock.AnythingOfType("map[string]dbus.Variant"),
		mock.AnythingOfType("services.DBusSecret"),
		true,
	).Return(errorCall(dbusErr))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	err := manager.SaveCredentials("api_key", "super-secret-value")

	assert.Error(t, err)
	assert.Contains(t, err.Error(), "api_key")
}

// ---------------------------------------------------------------------------
// GetCredentials tests
// ---------------------------------------------------------------------------

func TestGetCredentials_Success(t *testing.T) {
	conn := &mockDBusConn{}
	svcObj := &mockBusObject{}
	itemObj := &mockBusObject{}

	itemPath := dbus.ObjectPath("/org/freedesktop/secrets/collection/default/1")

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets")).
		Return(svcObj)
	conn.On("Object", "org.freedesktop.secrets", itemPath).
		Return(itemObj)

	svcObj.On("Call",
		"org.freedesktop.Secret.Service.SearchItems",
		dbus.Flags(0),
		map[string]string{"application": "RetroLauncher", "key": "api_key"},
	).Return(successCall([]dbus.ObjectPath{itemPath}, []dbus.ObjectPath{}))

	secret := services.NewDBusSecret(testSession, []byte("super-secret-value"))

	itemObj.On("Call",
		"org.freedesktop.Secret.Item.GetSecret",
		dbus.Flags(0),
		testSession,
	).Return(successCall(secret))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	value, err := manager.GetCredentials("api_key")

	assert.NoError(t, err)
	assert.Equal(t, "super-secret-value", value)
	conn.AssertExpectations(t)
}

func TestGetCredentials_NotFound(t *testing.T) {
	conn := &mockDBusConn{}
	svcObj := &mockBusObject{}

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets")).
		Return(svcObj)

	svcObj.On("Call",
		"org.freedesktop.Secret.Service.SearchItems",
		dbus.Flags(0),
		map[string]string{"application": "RetroLauncher", "key": "missing_key"},
	).Return(successCall([]dbus.ObjectPath{}, []dbus.ObjectPath{}))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	value, err := manager.GetCredentials("missing_key")

	assert.Error(t, err)
	assert.Empty(t, value)
	assert.Contains(t, err.Error(), "missing_key")
}

func TestGetCredentials_SearchError(t *testing.T) {
	conn := &mockDBusConn{}
	svcObj := &mockBusObject{}

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets")).
		Return(svcObj)

	dbusErr := errors.New("D-Bus: service unavailable")

	svcObj.On("Call",
		"org.freedesktop.Secret.Service.SearchItems",
		dbus.Flags(0),
		map[string]string{"application": "RetroLauncher", "key": "api_key"},
	).Return(errorCall(dbusErr))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	value, err := manager.GetCredentials("api_key")

	assert.Error(t, err)
	assert.Empty(t, value)
	assert.Contains(t, err.Error(), "api_key")
}

func TestGetCredentials_GetSecretError(t *testing.T) {
	conn := &mockDBusConn{}
	svcObj := &mockBusObject{}
	itemObj := &mockBusObject{}

	itemPath := dbus.ObjectPath("/org/freedesktop/secrets/collection/default/1")

	conn.On("Object", "org.freedesktop.secrets", dbus.ObjectPath("/org/freedesktop/secrets")).
		Return(svcObj)
	conn.On("Object", "org.freedesktop.secrets", itemPath).
		Return(itemObj)

	svcObj.On("Call",
		"org.freedesktop.Secret.Service.SearchItems",
		dbus.Flags(0),
		map[string]string{"application": "RetroLauncher", "key": "api_key"},
	).Return(successCall([]dbus.ObjectPath{itemPath}, []dbus.ObjectPath{}))

	dbusErr := errors.New("D-Bus: item locked")

	itemObj.On("Call",
		"org.freedesktop.Secret.Item.GetSecret",
		dbus.Flags(0),
		testSession,
	).Return(errorCall(dbusErr))

	manager := services.NewKeyringCredentialsManagerWithConn(conn, testSession)

	value, err := manager.GetCredentials("api_key")

	assert.Error(t, err)
	assert.Empty(t, value)
	assert.Contains(t, err.Error(), "api_key")
}
