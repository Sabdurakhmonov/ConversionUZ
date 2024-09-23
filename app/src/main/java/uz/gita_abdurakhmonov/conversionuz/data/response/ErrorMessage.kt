package uz.gita_abdurakhmonov.conversionuz.data.response

sealed interface ErrorMessage {
    data class Message(val message:String)
}