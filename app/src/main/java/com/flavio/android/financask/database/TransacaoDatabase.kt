package com.flavio.android.financask.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.flavio.android.financask.model.Transacao

@Database(entities = [ Transacao::class ], version = 1)
abstract class TransacaoDatabase : RoomDatabase(){

}