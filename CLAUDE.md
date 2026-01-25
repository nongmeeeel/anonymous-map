# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Anonymous Map - 카카오지도 기반 익명 낙서 공유 웹사이트 (MVP)

사용자가 지도 위 원하는 좌표에 글(낙서)을 남기고, 모든 사람이 그 지도를 공유하는 서비스.

## Build & Run Commands

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun

# 테스트 전체 실행
./gradlew test

# 단일 테스트 실행
./gradlew test --tests "com.yongman.SomeTestClass.testMethodName"
```

## Tech Stack

- Spring Boot 4.0.2 (Web MVC, Data JPA)
- Java 21
- MariaDB (localhost:3307)
- Gradle
- Lombok
- 카카오맵 API

## Architecture

**레이어 구조**: Controller → Service → Repository

**패키지**: `com.yongman`

## Conventions

### API 응답 형식
```json
{
  "success": true,
  "data": { ... },
  "timestamp": "2025-01-25T12:00:00"
}
```

### JPA 규칙
- 테이블명: 대문자 스네이크 케이스, 단수형 (USER, POST)
- 다대다(@ManyToMany) 관계 사용 금지
- 필요시 중간 엔티티를 만들어 일대다/다대일로 연결
- @ManyToOne, @OneToOne은 반드시 `fetch = FetchType.LAZY` 설정
- 필요시 fetch join 또는 EntityGraph로 조회

### Lombok
- 모든 엔티티/DTO에 Lombok 활용
