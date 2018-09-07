/*
 * Copyright (c) 2018 Alexander Yaburov
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package me.impa.knockonports.database

import android.arch.lifecycle.LiveData
import android.content.Context
import me.impa.knockonports.database.dao.SequenceDao
import me.impa.knockonports.database.entity.Sequence

class KnocksRepository(context: Context) {

    private var sequenceDao: SequenceDao
    private var sequenceList: LiveData<List<Sequence>>

    init {
        val db = KnocksDatabase.getInstance(context)!!
        sequenceDao = db.sequenceDao()
        sequenceList = sequenceDao.findAllSequences()
    }

    fun getSequenceList(): LiveData<List<Sequence>> = sequenceList

    fun deleteSequence(sequence: Sequence): Int = sequenceDao.deleteSequence(sequence)

    fun updateSequences(sequences: List<Sequence>) = sequenceDao.updateSequences(sequences)

    fun saveSequence(sequence: Sequence) {
        if (sequence.id == null) {
            sequence.id = sequenceDao.insertSequence(sequence)
        } else {
            sequenceDao.updateSequence(sequence)
        }
    }

}