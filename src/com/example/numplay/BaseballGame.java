package com.example.numplay;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class BaseballGame {
    static LinkedHashSet<Integer> gNums = new LinkedHashSet<>();   // 숫자야구 문제
    String gNum = "";
    boolean flag = false;
    static String inputNum;
    static ArrayList<Integer> tryCnt = new ArrayList<>();  // 전체 회차별 게임 진행횟수

    public BaseballGame() {
        // 객체 생성시 정답을 만들도록 함
        Random rand = new Random();
        do {
            int iVal = rand.nextInt(9)+1; // 1~9 범위 지정
            //System.out.println(iVal);
            if (gNums.contains(iVal)) {
                gNums.remove(iVal);
            } else {
                gNums.add(iVal);
            }
        } while (gNums.size() < 3);

//        for (int i=1; i<gNums.size(); i++) {
//            gNum += gNums[i];
//        }
        for (int g : gNums) {
            gNum += g;
        }
        System.out.println("gNums:" + gNums.toString());
        System.out.println("gNum:" + gNum);
    }

    public int play() {
        int thisTryCnt = 0;
        Scanner sc = new Scanner(System.in);
        while (true) {
            // 1. 유저에게 입력값 을 받음
            System.out.println(">> 환영합니다! 원하시는 번호를 입력해주세요.");
            System.out.println(">> 1.게임시작   2.기록보기   3.종료 및 초기화");
            int bCnt = 0; //볼 카운트
            int sCnt = 0; //스트라이크 카운트
            int nextStep = sc.nextInt();

            if (nextStep == 1) { // 게임시작 입력 시 -> 게임 진행
                while (true)
                {
                    System.out.println(">> 3가지 숫자를 입력하세요."); //포기하고 돌아가기?
                    inputNum = sc.next();
                    thisTryCnt++; //시도횟수 +1번
                    validateInput(inputNum);
                    //정답판별//
//                    System.out.println(flag);
//                    System.out.println(gNum);
//                    System.out.println(inputNum);
                    if (flag == true) {
                        System.out.println(">> @#$%@#$%@#$%@#정답입니다#$%&^#$%@#$%#$");
                        tryCnt.add(thisTryCnt);
                        break;
                    } else {
                        sCnt = countStrike(inputNum);
                        bCnt = countBall(inputNum);
                        if (sCnt == 0 && bCnt ==0) {
                            System.out.println(">> 아웃, 다시!");
                        } else {
                            System.out.println(">> " + sCnt + "스트라이크 " + bCnt + "볼, 다시!");
                        }
                        tryCnt.add(thisTryCnt);
                        continue;
                    }
                }
            } else if (nextStep == 2) { // 기록보기 입력 시
                displayGame();
                break;
            } else if (nextStep == 3) { // 종료 입력 시
                System.out.println(">> 숫자야구 게임을 종료합니다. 이전 기록이 초기화됩니다.");
                tryCnt.clear();
                break;
            } else {                        // 1~3 외의 값을 입력 시
                System.out.println(">> 올바른 숫자(1,2,3)를 입력해주세요! 처음으로 돌아갑니다.");
                continue;
            }
            // 2. 올바른 입력값을 받았는지 검증
            // 3. 게임 진행횟수 증가
            // 4. 스트라이크 개수 계산
            // 5. 정답여부 확인, 만약 정답이면 break 를 이용해 반복문 탈출
            // 6. 볼 개수 계산
            // 7. 힌트 출력
            // 게임 진행횟수 반환
            break;
        } //end of while

        return thisTryCnt;
    }

    protected boolean validateInput(String input) {
        if (gNum.equals(input)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private int countStrike(String input) {
        int totalStrike = 0;
        //1번째자리~3번째자리
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == gNum.charAt(i)) totalStrike++;
        }
        return totalStrike;
    }

    private int countBall(String input) {
        int totalBall = 0;
//        int[] iNums = new int[3];
//        for (int i = 0; i < input.length(); i++) {
//            iNums[i] = input.charAt(i);
//        }
//        System.out.println("iNums:"+iNums);
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < gNum.length(); j++) {
                if ((i!=j)&&(input.charAt(i)==gNum.charAt(j))) {
                    totalBall++;
                }
//                System.out.println(input.charAt(i));
//                System.out.println(gNum.charAt(j));
//                System.out.println("i:"+i+" j:"+j+" 볼카운트:"+totalBall);
            }
        }
        return totalBall;
    }

    private void displayGame() {
        System.out.println(">> [게임 기록]");
        for (int i = 0; i < tryCnt.size(); i++) {
            System.out.println(">> " + (i+1) + "번째 게임 시도 횟수 : " + tryCnt.get(i));
        }
    }

    public static void main(String[] args) { //test~~
        BaseballGame ex = new BaseballGame();
        //test area~~
        // System.out.println("실행?");
        String gNum = "";
        for (int g : gNums) {
            gNum += Integer.toString(g);
        }
        System.out.println("정답:"+gNum);
        ////////


        int sout =ex.play();
        System.out.println(sout);
    }

    public void init() {
        gNums.clear();
        gNum = "";
        flag = false;
        inputNum = "";
    }
}
