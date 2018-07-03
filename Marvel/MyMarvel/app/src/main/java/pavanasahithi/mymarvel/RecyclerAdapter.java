package pavanasahithi.mymarvel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    Context context;
    ArrayList<AllCharactersPojo> allpojos;
    final static String DATA="data_pojo";
    final static String POSITION="position";

    RecyclerAdapter(Context context, ArrayList<AllCharactersPojo> allpojos) {
        this.context = context;
        this.allpojos = allpojos;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_design, viewGroup, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerHolder recyclerHolder, int i) {
        final int x = i;
        Picasso.get().load(allpojos.get(x).getUrl()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(recyclerHolder.poster);
        recyclerHolder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, allpojos.get(x).getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,DisplayActivity.class);
                intent.putExtra(DATA, allpojos);
                intent.putExtra(POSITION,x);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(allpojos==null)
            return 0;
        return allpojos.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.id_poster);
        }
    }
}
