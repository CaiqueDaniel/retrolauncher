package services_test

import (
	"github.com/godbus/dbus/v5"
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
