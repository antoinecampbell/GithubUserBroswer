package com.antoinecampbell.githubuserbrowser.service;

import com.antoinecampbell.githubuserbrowser.home.HomeFragment;

import dagger.Component;

@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(HomeFragment fragment);
}
