package com.ip.ddangddangddang.domain.auction.repository;

import static com.ip.ddangddangddang.domain.auction.entity.QAuction.auction;

import com.ip.ddangddangddang.domain.auction.entity.Auction;
import com.ip.ddangddangddang.domain.auction.entity.StatusEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class AuctionQueryRepositoryImpl implements AuctionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Auction> findAllByFilters(List<Long> neighbor,
        StatusEnum status, String title, Pageable pageable
    ) {

        List<Auction> result = queryFactory.selectFrom(auction)
            .where(auction.townId.in(neighbor),
                eqStatusAndContainsTitle(status, title))//.in : List안에 있는 것 중에 찾는 것
//            .where(auction.townId.in(neighbor)
//                .and(eqStatusAndContainsTitle(status, title))) -> 이렇게도 가능
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(auction.createdAt.desc())
            .fetch();

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));

    }

    @Override
    public Slice<Auction> findAuctionsByUserId(Long userId, Pageable pageable) {

        List<Auction> result = queryFactory.selectFrom(auction)
            .where(auction.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(auction.createdAt.desc())
            .fetch();

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    @Override // Todo 해결해야해
    public Slice<Auction> findBidsByUserId(Long userId, Pageable pageable) {

        List<Auction> result = queryFactory.selectFrom(auction)
            .where(
                auction.buyerId.isNotNull()
                    .and(auction.buyerId.eq(userId))
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Auction> result, int pageSize) {
        if (result.size() > pageSize) {
            result.remove(pageSize);
            return true;
        }
        return false;
    }

    private BooleanExpression eqStatus(StatusEnum status) {
        if (status == null) {
            return null;
        }
        return auction.statusEnum.eq(status);
    }

    private BooleanExpression containsTitle(String title) {
        if (title == null) {
            return null;
        }
        return auction.title.contains(title);
    }

    private BooleanExpression eqStatusAndContainsTitle(StatusEnum status, String title) {
        BooleanExpression resultStatus = eqStatus(status);
        BooleanExpression resultContainsTitle = containsTitle(title);
        if (resultStatus != null && resultContainsTitle != null) {
            return resultStatus.and(resultContainsTitle);
        }
        if (resultStatus != null) {
            return resultStatus;
        }
        if (resultContainsTitle != null) {
            return resultContainsTitle;
        }
        return null;
    }

}
