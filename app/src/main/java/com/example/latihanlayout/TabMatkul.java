package com.example.latihanlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class TabMatkul extends Fragment {

    private String id;
    private EditText dosen;
    private EditText namamk;
    private EditText sks;
    private Button buttonSimpan;
    private Button buttonHapus;
    private Button buttonEdit;
    private FirebaseFirestore firebaseFirestoreDb;
    private CollectionReference fireRef;
    private FirestoreRecyclerAdapter<DataMatakuliah, DataViewHolder> adapter;
    private Query query;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mata_kuliah, container, false);
        namamk = view.findViewById(R.id.namaMk);
        recyclerView = view.findViewById(R.id.listfirestore);
        sks = view.findViewById(R.id.sks);
        dosen = view.findViewById(R.id.dosen);
        buttonSimpan = view.findViewById(R.id.simpanButton);
        buttonHapus = view.findViewById(R.id.hapusButton);
        buttonEdit = view.findViewById(R.id.editButton);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        fireRef = firebaseFirestoreDb.collection("DaftarMatKul");
        query = fireRef.orderBy("namamk", Query.Direction.ASCENDING);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!namamk.getText().toString().isEmpty() && !sks.getText().toString().isEmpty()&& !dosen.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                    namamk.setText("");
                    dosen.setText("");
                    sks.setText("");
                } else {
                    Toast.makeText(requireActivity(), "Data tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!namamk.getText().toString().isEmpty() && !sks.getText().toString().isEmpty()&& !dosen.getText().toString().isEmpty()) {
                    fireRef.document(id).delete();
                    namamk.setText("");
                    dosen.setText("");
                    sks.setText("");
                } else {
                    Toast.makeText(requireActivity(), "Data tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                DataMatakuliah matakuliah = documentSnapshot.toObject(DataMatakuliah.class);
                id = documentSnapshot.getId();
//                Toast.makeText(requireActivity(), id, Toast.LENGTH_SHORT).show();
                namamk.setText(documentSnapshot.getString("namamk"));
                dosen.setText(documentSnapshot.getString("dosen"));
                sks.setText(documentSnapshot.get("sks").toString());
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!namamk.getText().toString().isEmpty() && !sks.getText().toString().isEmpty() && !dosen.getText().toString().isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("namamk", namamk.getText().toString());
                    map.put("dosen", dosen.getText().toString());
                    map.put("sks",Integer.valueOf(sks.getText().toString()));
                    firebaseFirestoreDb.collection("DaftarMatKul").document(id).set(map);
                    namamk.setText("");
                    dosen.setText("");
                    sks.setText("");
                } else {
                    Toast.makeText(requireActivity(), "Data tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        FirestoreRecyclerOptions<DataMatakuliah> options = new FirestoreRecyclerOptions.Builder<DataMatakuliah>()
                .setQuery(query, DataMatakuliah.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<DataMatakuliah, DataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i, @NonNull DataMatakuliah data) {
                dataViewHolder.dsn.setText(data.getDosen());
                dataViewHolder.mtk.setText(data.getNamaMk());
                dataViewHolder.sks.setText(String.valueOf(data.getSks()));
            }

            @NonNull
            @Override
            public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_matakuliah, parent, false);
                return new DataViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public void deleteItem(int position){
        adapter.getSnapshots().getSnapshot(position).getReference().delete();
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {
        TextView mtk;
        TextView dsn;
        TextView sks;
        public DataViewHolder(View itemView){
            super(itemView);
            mtk = itemView.findViewById(R.id.matakuliah);
            dsn = itemView.findViewById(R.id.dosen);
            sks = itemView.findViewById(R.id.sks);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.OnItemClick(adapter.getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    private void tambahMahasiswa() {
        DataMatakuliah matkul = new DataMatakuliah(namamk.getText().toString(),
                Integer.parseInt(sks.getText().toString()),
                dosen.getText().toString());

        firebaseFirestoreDb.collection("DaftarMatKul").document().set(matkul)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Matakuliah berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }


}
