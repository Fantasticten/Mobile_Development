import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.data.dataTreatment

class TreatmentAdapter(private val treatmentList: List<dataTreatment>) :
    RecyclerView.Adapter<TreatmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggalTextView: TextView = itemView.findViewById(R.id.tgltratment)
        val layananTextView: TextView = itemView.findViewById(R.id.tratmentlayanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tratment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val treatment = treatmentList[position]

        // Set the date and treatment service in the TextViews
        holder.tanggalTextView.text = treatment.hari_tanggal
        holder.layananTextView.text = treatment.nama_layanan
    }

    override fun getItemCount(): Int {
        return treatmentList.size
    }
}