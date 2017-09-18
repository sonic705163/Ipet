package com.example.java.mybottomnav;


import java.util.List;

public interface AsyncListener {
    void onError();
    void onGoing(int progress);
    void onFinish(List<PetWallVO> petWallVO);
}
