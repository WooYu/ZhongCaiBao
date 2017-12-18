package com.developer.lecai.control;

import android.content.Context;
import android.widget.Toast;

import com.developer.lecai.bean.CaiPiaoWanFaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/8/14.
 */

public class CheckNumberController {
    
    /*private static CheckNumberController instance;
    public static CheckNumberController getInstance() {
        if(instance == null) {
            synchronized (UserController.class) {
                if(instance == null) {
                    instance = new CheckNumberController();
                }
            }
        }
        return instance;
    }*/

    public static boolean checkNumber(Context context, CaiPiaoWanFaBean CPWFBean, int[][] caiPiaoList) {
        List<Integer> bigList = new ArrayList<>();
        int big = -1;
        String cpName = CPWFBean.getPname();
        if (cpName.contains("五星通选-直选复式")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("三星直选-后三")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("三星组三-后三")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 2) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("三星组六-后三")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 3) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("二星直选-后二")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("二星组选-后二")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 2) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-三星一码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 1) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-四星一码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 1) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-三星二码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 2) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-四星二码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 2) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-五星二码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 2) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("不定胆-五星三码")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != j) {
                            big = j;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() >= 3) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("定位胆-五星定位胆")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("前三名-前三名")) {

            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            List<Integer> countFir=new ArrayList<>();
            List<Integer> countSec=new ArrayList<>();
            List<Integer> countThi=new ArrayList<>();
            for(int i: caiFir){
                if(i==100){
                    countFir.add(i);
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec.add(i);
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi.add(i);
                }
            }
            if(countFir.size()==0||countSec.size()==0||countThi.size()==0){
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
            int countTotal=0;
            for(int i: countFir){
                for(int j: countSec){
                    for(int k: countThi){
                        if(i!=j&&i!=k&&j!=k){
                            countTotal++;
                        }
                    }
                }
            }
            if (countTotal > 0) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("前二名-前二名")) {

            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            List<Integer> countFir=new ArrayList<>();
            List<Integer> countSec=new ArrayList<>();
            for(int i: caiFir){
                if(i==100){
                    countFir.add(i);
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec.add(i);
                }
            }
            if(countFir.size()==0||countSec.size()==0){
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
            int countTotal=0;
            for(int i: countFir){
                for(int j: countSec){
                    if(i!=j){
                        countTotal++;
                    }
                }
            }
            if (countTotal > 0) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("冠军-冠军")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("定位胆-前五")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (cpName.contains("定位胆-后五")) {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            for (int i = 0; i < caiPiaoList.length; i++) {
                for (int j = 0; j < 10; j++) {
                    if (caiPiaoList[i][j] == 100) {
                        if (big != i) {
                            big = i;
                            bigList.add(big);
                        }
                    }
                }
            }
            if (bigList.size() == caiPiaoList.length) {
                return true;
            } else {
                Toast.makeText(context, "请合法选球，否则无法购买彩票", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public static int checkNumberNoToast(Context context, CaiPiaoWanFaBean CPWFBean, int[][] caiPiaoList) {
        String cpName = CPWFBean.getPname();
        if (cpName.contains("五星通选-直选复式")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int[] caiFour=caiPiaoList[3];
            int[] caiFive=caiPiaoList[4];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            int countFour=0;
            int countFive=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            for(int i: caiFour){
                if(i==100){
                    countFour++;
                }
            }
            for(int i: caiFive){
                if(i==100){
                    countFive++;
                }
            }
            return countFir*countSec*countThi*countFour*countFive;

        } else if (cpName.contains("三星直选-后三")) {

            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            return countFir*countSec*countThi;

        } else if (cpName.contains("三星组三-后三")) {

            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=2 ? countF*(countF-1) : 0;

        } else if (cpName.contains("三星组六-后三")) {

            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=3 ? countF*(countF-1)*(countF-2)/6 : 0;
        } else if (cpName.contains("二星直选-后二")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int countFir=0;
            int countSec=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            return countFir*countSec;
        } else if (cpName.contains("二星组选-后二")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=2 ? countF*(countF-1)/2 : 0;
        } else if (cpName.contains("不定胆-三星一码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF;
        } else if (cpName.contains("不定胆-四星一码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF;
        } else if (cpName.contains("不定胆-三星二码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=2 ? countF*(countF-1)/2 : 0;
        } else if (cpName.contains("不定胆-四星二码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=2 ? countF*(countF-1)/2 : 0;
        } else if (cpName.contains("不定胆-五星二码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=2 ? countF*(countF-1)/2 : 0;
        } else if (cpName.contains("不定胆-五星三码")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF>=3 ? countF*(countF-1)*(countF-2)/6 : 0;
        } else if (cpName.contains("定位胆-五星定位胆")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int[] caiFour=caiPiaoList[3];
            int[] caiFive=caiPiaoList[4];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            int countFour=0;
            int countFive=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            for(int i: caiFour){
                if(i==100){
                    countFour++;
                }
            }
            for(int i: caiFive){
                if(i==100){
                    countFive++;
                }
            }
            //if(countFir!=0&&countFir!=0&&countFir!=0&&countFir!=0&&countFir!=0){
            if(countFir!=0||countSec!=0||countThi!=0||countFour!=0||countFive!=0){
                return countFir + countSec + countThi + countFour + countFive;
            }else {
                return 0;
            }
        } else if (cpName.contains("前三名-前三名")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            List<Integer> countFir=new ArrayList<>();
            List<Integer> countSec=new ArrayList<>();
            List<Integer> countThi=new ArrayList<>();
            for(int i = 0 ;i <caiFir.length;i++){
                if(caiFir[i] == 100){
                    countFir.add(i);
                }
            }
            for (int i = 0; i < caiSec.length; i++) {
                if(caiSec[i] == 100){
                    countSec.add(i);
                }
            }
            for (int i = 0; i < caiThi.length; i++) {
                if(caiThi[i] == 100){
                    countThi.add(i);
                }
            }

            if(countFir.size()==0||countSec.size()==0||countThi.size()==0){
                return 0;
            }
            int countTotal=0;
            for(int i: countFir){
                for(int j: countSec){
                    for(int k: countThi){
                        if(i!=j&&i!=k&&j!=k){
                            countTotal++;
                        }
                    }
                }
            }
            return countTotal;
        } else if (cpName.contains("前二名-前二名")) {

            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            List<Integer> countFir=new ArrayList<>();
            List<Integer> countSec=new ArrayList<>();
            for(int i = 0 ;i <caiFir.length;i++){
                if(caiFir[i] == 100){
                    countFir.add(i);
                }
            }
            for (int i = 0; i < caiSec.length; i++) {
                if(caiSec[i] == 100){
                    countSec.add(i);
                }
            }
            if(countFir.size()==0||countSec.size()==0){
                return 0;
            }
            int countTotal=0;
            for(int i: countFir){
                for(int j: countSec){
                    if(i!=j){
                        countTotal++;
                    }
                }
            }
            return countTotal;
        } else if (cpName.contains("冠军-冠军")) {
            int[] caiF=caiPiaoList[0];
            int countF=0;
            for(int i: caiF){
                if(i==100){
                    countF++;
                }
            }
            return countF;
        } else if (cpName.contains("定位胆-前五")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int[] caiFour=caiPiaoList[3];
            int[] caiFive=caiPiaoList[4];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            int countFour=0;
            int countFive=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            for(int i: caiFour){
                if(i==100){
                    countFour++;
                }
            }
            for(int i: caiFive){
                if(i==100){
                    countFive++;
                }
            }
            if(countFir!=0||countSec!=0||countThi!=0||countFour!=0||countFive!=0){
                return countFir + countSec + countThi + countFour + countFive;
            }else {
                return 0;
            }
           // return countFir*countSec*countThi*countFour*countFive;
        } else if (cpName.contains("定位胆-后五")) {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int[] caiFour=caiPiaoList[3];
            int[] caiFive=caiPiaoList[4];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            int countFour=0;
            int countFive=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            for(int i: caiFour){
                if(i==100){
                    countFour++;
                }
            }
            for(int i: caiFive){
                if(i==100){
                    countFive++;
                }
            }
            if(countFir!=0||countSec!=0||countThi!=0||countFour!=0||countFive!=0){
                return countFir + countSec + countThi + countFour + countFive;
            }else {
                return 0;
            }
           // return countFir*countSec*countThi*countFour*countFive;
        } else {
            int[] caiFir=caiPiaoList[0];
            int[] caiSec=caiPiaoList[1];
            int[] caiThi=caiPiaoList[2];
            int[] caiFour=caiPiaoList[3];
            int[] caiFive=caiPiaoList[4];
            int countFir=0;
            int countSec=0;
            int countThi=0;
            int countFour=0;
            int countFive=0;
            for(int i: caiFir){
                if(i==100){
                    countFir++;
                }
            }
            for(int i: caiSec){
                if(i==100){
                    countSec++;
                }
            }
            for(int i: caiThi){
                if(i==100){
                    countThi++;
                }
            }
            for(int i: caiFour){
                if(i==100){
                    countFour++;
                }
            }
            for(int i: caiFive){
                if(i==100){
                    countFive++;
                }
            }
            return countFir*countSec*countThi*countFour*countFive;
        }
    }

}
