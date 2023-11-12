package com.people4business.blogapp.data
data class EntradaBlog(val titulo: String?, val autor: String?, val contenido: String?, val fechaHora: String?)
data class EntradaBlogResponse(val code: Int?, val meta: String?, val data: EntradaBlog?)