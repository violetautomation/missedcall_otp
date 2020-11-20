package com.viol8.stgvirtual.koin

import com.viol8.stgvirtual.modules.session.viewmodel.AuthViewModel
import com.viol8.stgvirtual.repositories.AuthRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    single { AuthRepository() }

    viewModel { AuthViewModel(get()) }
}