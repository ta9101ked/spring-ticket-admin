# テスト仕様書（学習用）

- 文書バージョン: 1.0

## 1. テスト方針

- 自動テスト: JUnit 5 + Spring Boot Test / MockMvc / DataJpaTest
- 手動テスト: ブラウザでの確認（スクリーンショット付き）

## 2. 自動テスト一覧

| ID | クラス | 観点 | 期待 |
|---|---|---|---|
| AT-01 | TicketRepositoryTest | status 絞り込み | 一致行のみ |
| AT-02 | TicketRepositoryTest | title×status AND | 1件に絞れる |
| AT-03 | TicketRepositoryTest | existsByTicketNo | 重複検知 |
| AT-04 | TicketServiceTest | 重複 create | Duplicate 例外 |
| AT-05 | TicketServiceTest | 欠番 findById | NotFound 例外 |
| AT-06 | TicketServiceTest | search | 件数どおり |
| AT-07 | TicketServiceTest | update | 同一 ID で更新 |
| AT-08 | TicketControllerTest | GET /tickets | index 表示 |
| AT-09 | TicketControllerTest | 不正 POST | new 再表示 |
| AT-10 | TicketControllerTest | 正常 POST | /tickets へ redirect |
| AT-11 | TicketControllerTest | 欠番 edit | not-found |

実行:

```bash
./mvnw test
```

## 3. 手動テスト一覧

| ID | 手順 | 期待 |
|---|---|---|
| MT-01 | 空で Create | 必須エラー表示 |
| MT-02 | select で登録 | 一覧に Status/Priority 表示 |
| MT-03 | 既存 ticketNo で再登録 | 重複エラー |
| MT-04 | status=In_Progress 検索 | 該当のみ |
| MT-05 | keyword + status | AND 結果 |
| MT-06 | Edit して更新 | 行が増えず内容変更 |
| MT-07 | Delete 確認 OK | 行が減る |
| MT-08 | `/tickets/999/edit` | Not Found |

## 4. 証跡

`docs/screenshots/` に配置。
