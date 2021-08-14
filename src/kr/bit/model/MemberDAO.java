package kr.bit.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// JDBC ->myBatis, JPA

public class MemberDAO {
	private static SqlSessionFactory sqlSessionFactory;
	
	// 초기화 블럭 - 프로그램 실행시 딱 한번만 실행되는 코드 영역
	static {
		String resource = "kr/bit/mybatis/config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 회원 전체 리스트보기
	public List<MemberVO> memberList() {
		// Connection+Statement => SqlSession
		SqlSession session = sqlSessionFactory.openSession();
		List<MemberVO> list=session.selectList("memberList");
		session.close(); //세션 반납
		return list;
	}
	// 회원 가입
	public int memberInsert(MemberVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.insert("memberInsert", vo);
		session.commit(); // select를 제외하고는 commit을 해줘야 정상 작동한다.
		session.close();
		return cnt;
	}
	// 회원 삭제
	public int memberDelete(int num) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.delete("memberDelete", num);
		session.commit();
		session.close();
		return cnt;
	}
	//회원 상세보기
	public MemberVO memberContent(int num) {
		SqlSession session=sqlSessionFactory.openSession();
		MemberVO vo =session.selectOne("memberContent", num);
		session.close();
		return vo;
	}
	//회원 수정하기
	public int memberUpdate(MemberVO vo) {
		SqlSession session=sqlSessionFactory.openSession();
		int cnt = session.update("memberUpdate", vo);
		session.commit();
		session.close();
		return cnt;
	}
}
