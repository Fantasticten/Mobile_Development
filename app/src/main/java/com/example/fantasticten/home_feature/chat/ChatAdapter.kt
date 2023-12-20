package com.example.fantasticten.home_feature.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.fantasticten.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(context: Context, private val resourceId: Int, private val messages: List<Message>) :
    ArrayAdapter<Message>(context, resourceId, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null)
            holder = ViewHolder()
            holder.messageLayout = view.findViewById(R.id.messageLayout)
            holder.messageTextView = view.findViewById(R.id.messageTextView)
            holder.timestampTextView = view.findViewById(R.id.timestampTextView)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val message = messages[position]
        holder.messageTextView?.text = message.text

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        if (message.senderId == 13) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
            holder.messageLayout?.setBackgroundResource(R.drawable.bubble_left)
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
            holder.messageLayout?.setBackgroundResource(R.drawable.bubble_right)
        }

        holder.messageLayout?.layoutParams = layoutParams

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val inputFormatAlternative = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormatAlternative = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            val date = inputFormat.parse(message.timestamp)
            holder.timestampTextView?.text = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            try {
                val alternativeDate = inputFormatAlternative.parse(message.timestamp)
                holder.timestampTextView?.text = outputFormatAlternative.format(alternativeDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                holder.timestampTextView?.text = "Invalid Time"
            }
        }


        return view!!
    }

    private class ViewHolder {
        var messageLayout: RelativeLayout? = null
        var messageTextView: TextView? = null
        var timestampTextView: TextView? = null
    }
}
