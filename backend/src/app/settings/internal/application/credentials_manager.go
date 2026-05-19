package application

type CredentialsManager interface {
	SaveCredentials(key, value string) (err error)
	GetCredentials(key string) (value string, err error)
}
