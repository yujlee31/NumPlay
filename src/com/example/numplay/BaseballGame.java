package com.example.numplay;

import java.util.*;

public class BaseballGame {
    static LinkedHashSet<Integer> gNums = new LinkedHashSet<>();   // 숫자야구 문제
    String gNum = "";
    static boolean flagForNewGame = true;
    static String inputNum;
    static ArrayList<Integer> tryCnt = new ArrayList<>();  // 전체 회차별 게임 진행횟수

    public BaseballGame() {
        // 객체 생성시 정답을 만들도록 함
        Random rand = new Random();
        do {
            int iVal = rand.nextInt(9)+1; // 1~9 범위 지정

            if (gNums.contains(iVal)) {
                gNums.remove(iVal);
            } else {
                gNums.add(iVal);
            }
        } while (gNums.size() < 3);

        for (int g : gNums) {
            gNum += g;
        }
        ///System.out.println("정답:" + gNum);
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
                while (true) {
                    System.out.println(">> 세 개의 숫자를 입력하세요.");
                    inputNum = sc.next();
                    thisTryCnt++; //시도횟수 +1번
                    boolean flag = validateInput(inputNum);
                    if (flag == true) { //사용자 입력값에 문제가 없을 때
                        if (gNum.equals(inputNum)) {
                            System.out.println(">> :+::+::+::+::+:정답입니다:+::+::+::+::+:");
                            System.out.println();
                            flagForNewGame = true;
                            tryCnt.add(thisTryCnt);
                            break;
                        } else {
                            sCnt = countStrike(inputNum);
                            bCnt = countBall(inputNum);
                            if (sCnt == 0 && bCnt ==0) {
                                System.out.println(">> 아웃, 다시!");
                                continue;
                            } else {
                                System.out.println(">> " + sCnt + "스트라이크 " + bCnt + "볼, 다시!");
                                continue;
                            }
                        }
                    }
                } //end of while (Playing)
            } else if (nextStep == 2) { // 기록보기 입력 시
                displayGame();
                flagForNewGame = true;
                break;
            } else if (nextStep == 3) { // 종료 입력 시
                System.out.println(">> 숫자야구 게임을 종료합니다. . .");
                flagForNewGame = false;
                tryCnt.clear(); //이전 기록 모두 지우기
                break;
            } else { // 1~3 외의 값을 입력 시
                System.out.println(">> 올바른 숫자(1,2,3)를 입력해주세요! 처음으로 돌아갑니다.");
                continue;
            }
            break;
        } //end of while (Start)

        return thisTryCnt;
    }

    protected boolean validateInput(String input) {
        boolean validateInputFlag = true;
        HashSet<Character> validCheckSet = new HashSet<>();
        for (int i=0; i<inputNum.length(); i++) {
            if (inputNum.charAt(i) != '0') {
                validCheckSet.add(inputNum.charAt(i));
            }
        }
        while (true) {
            try {

                //1. 문자입력불가 : nfe
                Integer tempInt = Integer.parseInt(input);
                //2. 3자리 수 & 중복 예외처리
                Exception e = new Exception();
                if (validCheckSet.size() != 3) {
                    throw e;
                } else {
                    validateInputFlag =  true;
                    break;
                }
            } catch (NumberFormatException nfe) { //문자 입력 시 catch
                System.out.println(">>[error]문자는 입력할 수 없습니다.");
                validateInputFlag = false;
                break;
            }  catch (Exception e) {
//                if (validCheckSet.contains(0)) {
//                    System.out.println(">>[error] 0은 입력할 수 없습니다.");
//                }
                if (validCheckSet.size() != 3) {
                    System.out.println(">>[error] 잘못된 숫자 형식입니다. 0을 제외한 중복되지 않는 3개의 수를 입력하세요.");
                }
                validateInputFlag = false;
                break;
            }
        }
        return validateInputFlag;
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
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < gNum.length(); j++) {
                if ((i!=j)&&(input.charAt(i)==gNum.charAt(j))) {
                    totalBall++;
                }
            }
        }
        return totalBall;
    }

    private void displayGame() {
        System.out.println(">> [게임 기록]");
        for (int i = 0; i < tryCnt.size(); i++) {
            System.out.println(">> " + (i+1) + "번째 게임 시도 횟수 : " + tryCnt.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) { //test~~
        while (flagForNewGame == true) {
            BaseballGame ex = new BaseballGame();
            ex.play();
            gNums.clear();
        }
    }
}
