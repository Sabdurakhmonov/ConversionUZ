package uz.gita.conversionuz.data.response

sealed interface ErrorMessage {
    data class Message(val message:String)
}