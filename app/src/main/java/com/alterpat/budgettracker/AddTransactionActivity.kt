package com.alterpat.budgettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        labelInput.addTextChangedListener {
            if(it!!.isNotEmpty())
                labelLayout.error = null
        }
        amountInput.addTextChangedListener {
            if(it!!.isNotEmpty())
                amountLayout.error = null
        }


        addItemBtn.setOnClickListener {
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descriptionInput.text.toString()

            if(label.isEmpty())
                labelLayout.error = "Please enter valid label"

            else if(amount == null)
                amountLayout.error = "Please enter valid amount"

            else{
                val transaction = Transaction(0, label, amount, description)
                insert(transaction)
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun insert(transaction: Transaction){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }

}