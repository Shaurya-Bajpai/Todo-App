package com.example.to_dolist.graph

import android.app.Application

class TodoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}