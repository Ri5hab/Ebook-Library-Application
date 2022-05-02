package riscrypt.tiwed.indianebooklibrary4;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static java.security.AccessController.getContext;

public class myAdapter extends FirebaseRecyclerAdapter<Model,myAdapter.myviewholder>//implements Filterable
{

    public myAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, int position, @NonNull final Model model) {

        holder.name.setText(model.getName());
        holder.pag.setText(model.getPag());
        holder.py.setText(model.getPy());
        holder.pby.setText(model.getPby());
        Glide.with(holder.img.getContext()).load(model.getImgurl()).into(holder.img);

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.b1.getContext(), Main2Activity.class);
                intent.putExtra("Pdfurl", model.getPdfurl());
                // intent.putExtra("Name",model.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.b1.getContext().startActivity(intent);
                Toast.makeText(holder.b2.getContext(), "Please Wait..", Toast.LENGTH_LONG).show();
            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(holder.b2.getContext(), Razorpay.class);
                intent2.putExtra("Pdfurl", model.getPdfurl());
                intent2.putExtra("Name",model.getName());
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.b2.getContext().startActivity(intent2);
                Toast.makeText(holder.b2.getContext(), "Please Wait..", Toast.LENGTH_LONG).show();
            }

        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singelrow, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        int x = 600, y = 1050;
        Button b1, b2;
        TextView name, py, pag, pby;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.t1);
            pag = (TextView) itemView.findViewById(R.id.t2);
            py = (TextView) itemView.findViewById(R.id.t3);
            pby = (TextView) itemView.findViewById(R.id.t4);
            b1 = (Button) itemView.findViewById(R.id.b1);
            b2 = (Button) itemView.findViewById(R.id.b2);
        }
    }
}