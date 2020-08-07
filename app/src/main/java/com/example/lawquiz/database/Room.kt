package com.example.lawquiz.database

import DatabaseTest
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lawquiz.R

@Dao
interface UserDao{

}
@Dao
interface QuestionDao{

}

@Dao
interface TestDao{

}

@Database(entities = [DatabaseUser::class,DatabaseQuestion::class,DatabaseTest::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val queDao : QuestionDao
    abstract val testDao : TestDao
}

private lateinit var INSTANCE: QuizDatabase

fun getDatabase(context: Context):QuizDatabase{
    synchronized(QuizDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                QuizDatabase::class.java,
                context.getString(R.string.database_name)).build()
        }
    }
    return INSTANCE
}