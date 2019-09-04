package com.github.neighbortrader.foodboardapp.transfer;

        import android.content.Context;
        import android.os.AsyncTask;

        import java.util.List;

public abstract class GetRequest<T> extends AsyncTask<Void, Void, List<T>> {

    private OnEventListener<T> callBack;
    private Context context;
    public Exception exception;

    public GetRequest(Context context, OnEventListener callback) {
        callBack = callback;
        this.context = context;
    }

    @Override
    abstract protected List<T> doInBackground(Void... params);

    @Override
    protected void onPostExecute(List<T> result) {
        if (callBack != null) {
            if (exception == null) {
                callBack.onResponse(result);
            } else {
                callBack.onFailure(exception);
            }
        }
    }
}
