package com.example.tasktracker.persistence.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File // For JVM file path

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(dbFile: File): AppDatabase { // Pass a File object for JVM
            return INSTANCE ?: synchronized(this) {
                // For KMP JVM, Room.databaseBuilder needs specific setup for JVM
                // The 'name' parameter is the database file path.
                // We typically don't pass Android Context.
                // A factory might be needed for the RoomDatabase_Impl.
                val instance = Room.databaseBuilder<AppDatabase>(
                    name = dbFile.absolutePath // Use absolute path for the DB file
                )
                // For KMP JVM, using `klass.java` might be needed with `Room.databaseBuilder`
                // or a custom factory that instantiates the _Impl class.
                // Refer to specific Room KMP Desktop examples for exact builder.
                // This is a common point of complexity for non-Android Room.
                // One common pattern for JVM:
                // val instance = Room.databaseBuilder(
                //     context = Any(), // Dummy context, not used by JVM builder with path
                //     klass = AppDatabase::class.java,
                //     name = dbFile.absolutePath
                // )
                // For pure KMP JVM with newer Room, a direct builder might exist.
                // Let's try a simpler form that usually works if _Impl is found by KSP:
                // This relies on KSP generating the correct factory method or Room being
                // able to instantiate the Impl class.
                .setDriver(BundledSQLiteDriver()) // Recommended for KMP Room to bundle driver
                .build()
                // If the above .build() fails, you might need something like:
                // .factory { dbFile ->
                //    SQLiteConnection(dbFile).run {
                //        configurePragmas() // If you have custom PRAGMAS
                //        create qilishda AppDatabase_Impl()
                //    }
                //  }
                // Or more simply, if the reflection based _Impl works:
                // .factory { AppDatabase::class.instantiateImpl() } // Using the helper below

                INSTANCE = instance
                instance
            }
        }
    }
}

// Helper for KSP/Room KMP if constructor isn't public (less needed with newer Room KMP versions)
// private fun <T : RoomDatabase> KClass<T>.instantiateImpl(): T {
//    val implClassName = "${qualifiedName}_Impl"
//    return Class.forName(implClassName).getDeclaredConstructor().newInstance() as T
// }