package com.idealtap.pairme.common

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class CompletionLiveData<T> : LiveData<Resource<Boolean?>?>(), OnCompleteListener<T> {
    override fun onComplete(task: Task<T>) {
        value =
            if (task.isSuccessful) Resource(true)
            else Resource(task.exception!!)
    }
}