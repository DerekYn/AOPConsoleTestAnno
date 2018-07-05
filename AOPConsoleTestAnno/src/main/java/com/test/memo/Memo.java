package com.test.memo;

public class Memo implements IMemo {
	
	@Override
	public void write(String memo) {
		
		for(int i=0; i<10; i++) {
			System.out.println("메모 : " + memo);	
		}
	}
	
	@Override	// 주업무 추가하기 - 메모 수정하기
	public void edit(String memo) {
		
		String orginMemo = "JAVA";
		
		System.out.println("메모수정 전 : " + orginMemo);
		
		orginMemo = memo;
		System.out.println("메모수정 후 : " + orginMemo);
		
	}
	
	@Override
	public int del(int seq) {

		System.out.println(seq + "번 메모 삭제");
		
		return seq;
	}
	
	@Override
	public void read(int seq) throws Exception {
		
		if(seq < 1) {
			throw new Exception("번호 오류 " + seq);
		}
		
		System.out.println(seq + "번 메모 읽기");
		
	}

}
