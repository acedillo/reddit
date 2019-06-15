package com.cedillo.reddit.model

data class Parent (var modhash : String? = null,
                   var dist: Int = 0,
                   var children : List<Child>? = null)