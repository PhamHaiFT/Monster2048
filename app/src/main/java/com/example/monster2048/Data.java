package com.example.monster2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Data {

    private static final String TAG = "Data";
    private static Data data;

    static {
        data = new Data();
    }

    public static Data getData() {
        return data;
    }

    public ArrayList<Integer> getListNumber() {
        return listNumber;
    }

    private ArrayList<Integer> listNumber = new ArrayList<>();
    private int[] listColor;
    private int[][] twoDimensionArray = new int[4][4];
    private Random random = new Random();

    private static int score = 0;
    private static boolean canMove = true;

    public void init(Context context) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                twoDimensionArray[i][j] = 0;
                listNumber.add(twoDimensionArray[i][j]);
            }
        }
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.listColor);
        listColor = new int[typedArray.length()];
        for (int i = 0; i < listColor.length; i++) {
            listColor[i] = typedArray.getColor(i, 0);
        }
        typedArray.recycle();

        addNumber();
        updateListNumber();
    }

    private static int countNewNumber;

    public int colorValue(int value) {
        int re = -1;
        if (value == 0) {
            re = Color.WHITE;
        } else {
            int index = (int) (Math.log(value) / Math.log(2));
            re = listColor[index - 1];
        }
        return re;
    }

    public void addNumber() {
        int count = 0;
        for (int i = 0; i < listNumber.size(); i++) {
            if (listNumber.get(i) == 0) {
                count++;
            }
        }
        if (count > 1) {
            countNewNumber = random.nextInt(2) + 1;
        } else if (count == 1) {
            countNewNumber = 1;
        } else {
            countNewNumber = 0;
        }
        while (countNewNumber != 0) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);
            if (twoDimensionArray[i][j] == 0) {
                twoDimensionArray[i][j] = 2;
                countNewNumber--;
            }
        }
    }

    public void updateListNumber() {
        listNumber.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                listNumber.add(twoDimensionArray[i][j]);
            }
        }
    }

    public int getScore() {
        return score;
    }


    public void swipeLeft() {
        //Sum number
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        int soX = twoDimensionArray[i][k];
                        if (soX == 0) {
                            continue;
                        } else {
                            if (so == soX) {
                                twoDimensionArray[i][j] = so * 2;
                                twoDimensionArray[i][k] = 0;
                                score += so;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        //Collapse row
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    for (int k = j + 1; k < 4; k++) {
                        int soX = twoDimensionArray[i][k];
                        if (soX == 0) {
                            continue;
                        } else {
                            twoDimensionArray[i][j] = soX;
                            twoDimensionArray[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }

        //update
        addNumber();
        updateListNumber();
    }

    public void swipeRight() {
        //Sum number
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        int soX = twoDimensionArray[i][k];
                        if (soX == 0) {
                            continue;
                        } else {
                            if (so == soX) {
                                twoDimensionArray[i][j] = so * 2;
                                twoDimensionArray[i][k] = 0;
                                score += so;

                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        //Collapse row
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    for (int k = j - 1; k >= 0; k--) {
                        int soX = twoDimensionArray[i][k];
                        if (soX == 0) {
                            continue;
                        } else {
                            twoDimensionArray[i][j] = soX;
                            twoDimensionArray[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }

        //update
        addNumber();
        updateListNumber();
    }

    public void swipeUp() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    continue;
                } else {
                    for (int k = i + 1; k < 4; k++) {
                        int soX = twoDimensionArray[k][j];
                        if (soX == 0) {
                            continue;
                        } else {
                            if (so == soX) {
                                twoDimensionArray[i][j] = so * 2;
                                twoDimensionArray[k][j] = 0;
                                score += so;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        //Collapse row
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    for (int k = i + 1; k < 4; k++) {
                        int soX = twoDimensionArray[k][j];
                        if (soX == 0) {
                            continue;
                        } else {
                            twoDimensionArray[i][j] = soX;
                            twoDimensionArray[k][j] = 0;
                            break;
                        }
                    }
                }
            }
        }

        //update
        addNumber();
        updateListNumber();
    }

    public void swipeDown() {
        //Sum number
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    continue;
                } else {
                    for (int k = i - 1; k >= 0; k--) {
                        int soX = twoDimensionArray[k][j];
                        if (soX == 0) {
                            continue;
                        } else {
                            if (so == soX) {
                                twoDimensionArray[i][j] = so * 2;
                                twoDimensionArray[k][j] = 0;
                                score += so;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        //Collapse row
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = twoDimensionArray[i][j];
                if (so == 0) {
                    for (int k = i - 1; k >= 0; k--) {
                        int soX = twoDimensionArray[k][j];
                        if (soX == 0) {
                            continue;
                        } else {
                            twoDimensionArray[i][j] = soX;
                            twoDimensionArray[k][j] = 0;
                            break;
                        }
                    }
                }
            }
        }

        //update
        addNumber();
        updateListNumber();
    }

    public boolean checkCanMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (twoDimensionArray[i][j] == 0) {
                    return true;
                }
            }
        }

        //Duyet theo dong
        int pointerRow = 0;
        for (int row = 0; row < 4; row++) {
            for (int k = 1; k < 4; k++) {
                if (twoDimensionArray[row][pointerRow] == twoDimensionArray[row][k]) {
                    return true;
                } else {
                    pointerRow++;
                    continue;
                }
            }
            pointerRow = 0;
        }

        //Duyet theo cot
        int pointerCol = 0;
        for (int col = 0; col < 4; col++) {
            for (int k = 1; k < 4; k++) {
                if (twoDimensionArray[pointerCol][col] == twoDimensionArray[k][col]) {
                    return true;
                } else {
                    pointerCol++;
                    continue;
                }
            }
            pointerCol = 0;
        }
        return false;
    }
}
