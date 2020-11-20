package com.viol8.stgvirtual.koin

import com.viol8.stgvirtual.modules.home.viewmodel.HomeViewModel
import com.viol8.stgvirtual.modules.session.viewmodel.AuthViewModel
import com.viol8.stgvirtual.repositories.AuthRepository
import com.viol8.stgvirtual.repositories.HomeRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    single { HomeRepository() }

    viewModel { HomeViewModel(get()) }
}