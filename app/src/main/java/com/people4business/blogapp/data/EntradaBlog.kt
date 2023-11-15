package com.people4business.blogapp.data
data class EntradaBlog(val titulo: String?= null, val autor: String?= null, val contenido: String?= null, val fechaHora: String?= null)
data class EntradaBlogResponse(val code: Int?, val meta: String?, val data: EntradaBlog?)




