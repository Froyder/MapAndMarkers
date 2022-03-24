package com.example.mapandmarkers.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapandmarkers.R
import com.example.mapandmarkers.database.RoomMarker
import com.example.mapandmarkers.interfaces.OnDeleteItemClickListener
import com.example.mapandmarkers.interfaces.OnListItemClickListener
import org.koin.core.component.KoinComponent

class MarkersAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var onDeleteItemClickListener: OnDeleteItemClickListener
) :
    RecyclerView.Adapter<MarkersAdapter.RecyclerItemViewHolder>(), KoinComponent {

    private var markersList = mutableListOf<RoomMarker>()

    fun setMarkersList(markersList: List<RoomMarker>){
        this.markersList = markersList as MutableList<RoomMarker>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.markers_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(markersList[position])
    }

    override fun getItemCount(): Int {
        return markersList.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(marker: RoomMarker) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(itemView){
                    setOnClickListener { openMarkerMenu(marker) }
                    findViewById<TextView>(R.id.marker_title).text = marker.title
                    findViewById<TextView>(R.id.marker_lat).text = itemView.context.getString(R.string.lat, marker.lat.toString())
                    findViewById<TextView>(R.id.marker_lng).text = itemView.context.getString(R.string.lat, marker.lng.toString())
                    findViewById<ImageButton>(R.id.marker_delete).setOnClickListener { deleteMarker(marker) }
                    if(marker.description != "")
                        findViewById<TextView>(R.id.marker_description).text = itemView.context.getString(R.string.note, marker.description)
                }
            }
        }
    }

    private fun openMarkerMenu(listItemDataModel: RoomMarker) {
        onListItemClickListener.onItemClick(listItemDataModel)
    }

    private fun deleteMarker(listItemDataModel: RoomMarker){
        onDeleteItemClickListener.onDeleteItem(listItemDataModel)
    }
}