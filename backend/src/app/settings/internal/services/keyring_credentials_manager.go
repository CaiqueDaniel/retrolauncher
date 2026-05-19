package services

import (
	"fmt"
	"retrolauncher/backend/src/app/settings/internal/application"

	"github.com/godbus/dbus/v5"
)

const (
	secretServiceBus        = "org.freedesktop.secrets"
	secretServicePath       = "/org/freedesktop/secrets"
	secretServiceCollection = "/org/freedesktop/secrets/aliases/default"
	secretServiceIface      = "org.freedesktop.Secret.Service"
	secretCollectionIface   = "org.freedesktop.Secret.Collection"
	secretItemIface         = "org.freedesktop.Secret.Item"
	appLabel                = "RetroLauncher"
)

// DBusConn abstracts the D-Bus session connection for testability.
type DBusConn interface {
	Object(dest string, path dbus.ObjectPath) dbus.BusObject
}

type keyringCredentialsManager struct {
	conn    DBusConn
	session dbus.ObjectPath
}

// NewKeyringCredentialsManager creates a new CredentialsManager backed by the
// system's Secret Service (GNOME Keyring, KWallet, etc.) via D-Bus.
func NewKeyringCredentialsManager() (application.CredentialsManager, error) {
	conn, err := dbus.SessionBus()

	if err != nil {
		return nil, fmt.Errorf("credentials manager: failed to connect to D-Bus session: %w", err)
	}

	sessionPath, err := openSession(conn)

	if err != nil {
		return nil, err
	}

	return &keyringCredentialsManager{conn: conn, session: sessionPath}, nil
}

// NewKeyringCredentialsManagerWithConnForTests is used by tests to inject a mock connection.
func NewKeyringCredentialsManagerWithConnForTests(conn DBusConn, session dbus.ObjectPath) *keyringCredentialsManager {
	return &keyringCredentialsManager{conn: conn, session: session}
}

func openSession(conn DBusConn) (dbus.ObjectPath, error) {
	svc := conn.Object(secretServiceBus, secretServicePath)

	var sessionPath dbus.ObjectPath
	var output dbus.Variant

	err := svc.Call(secretServiceIface+".OpenSession", 0, "plain", dbus.MakeVariant("")).Store(&output, &sessionPath)
	if err != nil {
		return "", fmt.Errorf("credentials manager: failed to open Secret Service session: %w", err)
	}

	return sessionPath, nil
}

// SaveCredentials stores a secret under the given key in the default keyring collection.
func (m *keyringCredentialsManager) SaveCredentials(key, value string) error {
	collection := m.conn.Object(secretServiceBus, secretServiceCollection)

	attributes := map[string]string{
		"application": appLabel,
		"key":         key,
	}

	secret := buildSecret(m.session, value)

	properties := map[string]dbus.Variant{
		"org.freedesktop.Secret.Item.Label":      dbus.MakeVariant(fmt.Sprintf("%s: %s", appLabel, key)),
		"org.freedesktop.Secret.Item.Attributes": dbus.MakeVariant(attributes),
	}

	var itemPath dbus.ObjectPath
	var prompt dbus.ObjectPath

	err := collection.Call(secretCollectionIface+".CreateItem", 0, properties, secret, true).Store(&itemPath, &prompt)
	if err != nil {
		return fmt.Errorf("credentials manager: failed to save credentials for key %q: %w", key, err)
	}

	return nil
}

// GetCredentials retrieves the secret stored under the given key from the default keyring collection.
func (m *keyringCredentialsManager) GetCredentials(key string) (string, error) {
	svc := m.conn.Object(secretServiceBus, secretServicePath)

	attributes := map[string]string{
		"application": appLabel,
		"key":         key,
	}

	var itemPaths []dbus.ObjectPath
	var lockedPaths []dbus.ObjectPath

	err := svc.Call(secretServiceIface+".SearchItems", 0, attributes).Store(&itemPaths, &lockedPaths)
	if err != nil {
		return "", fmt.Errorf("credentials manager: failed to search items for key %q: %w", key, err)
	}

	if len(itemPaths) == 0 {
		return "", fmt.Errorf("credentials manager: no credentials found for key %q", key)
	}

	item := m.conn.Object(secretServiceBus, itemPaths[0])

	var secret DBusSecret

	err = item.Call(secretItemIface+".GetSecret", 0, m.session).Store(&secret)
	if err != nil {
		return "", fmt.Errorf("credentials manager: failed to retrieve secret for key %q: %w", key, err)
	}

	return string(secret.Value), nil
}

// DBusSecret matches the org.freedesktop.Secret.Secret struct (a(oayays)).
// Exported so tests can construct expected values without re-declaring the type.
type DBusSecret struct {
	Session     dbus.ObjectPath
	Parameters  []byte
	Value       []byte
	ContentType string
}

// NewDBusSecret creates a DBusSecret for use in tests.
func NewDBusSecret(session dbus.ObjectPath, value []byte) DBusSecret {
	return DBusSecret{
		Session:     session,
		Parameters:  []byte{},
		Value:       value,
		ContentType: "text/plain; charset=utf8",
	}
}

func buildSecret(session dbus.ObjectPath, value string) DBusSecret {
	return NewDBusSecret(session, []byte(value))
}
