package co.id.fastpay.fastpaynotification.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.id.fastpay.fastpaynotification.ui.NotificationDetailViewModel;
import co.id.fastpay.fastpaynotification.ui.NotificationViewModel;

/**
 * Created by ${Saquib} on 12-08-2018.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    public ViewModelFactory() {
        this.repository = new Repository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NotificationViewModel.class)) {
            return (T) new NotificationViewModel(repository);
        }
        else if (modelClass.isAssignableFrom(NotificationDetailViewModel.class)) {
            return (T) new NotificationDetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
