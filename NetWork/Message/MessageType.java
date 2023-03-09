package NetWork.Message;

public enum MessageType {
    LOGIN_REQUEST,
    LOGIN_RESPONSE,
    REGISTER_REQUEST,
    REGISTER_RESPONSE,
    LOGOUT,
    //un messaggio generale da inviare, una frase
    MESSAGE,
    //un messaggio che chiede un certo oggetto da restituire, esempio una spesa
    DATA_REQUEST,
    //risposta di un oggetto generale
    DATA_RESPONSE,
    DISCONNECT,
    EXPENSE,
    ERROR
}
