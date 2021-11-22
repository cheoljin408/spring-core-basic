package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    /*
        위의 코드는 FixDiscountPolicy에서 RateDiscountPolicy로 구현체를 바꾸면 클라이언트인 OrderServiceImpl의 코드도 함께 변경해야 하므로
        OCP 위반에 해당한다

        또한 OrderServiceImpl은 DiscountPolicy 인터페이스 뿐만 아니라 구체클래스도 함께 의존하므로 DIP 위반에도 해당한다
     */
    // 인터페이스에만 의존하도록 설계를 변경해야 한다
    private DiscountPolicy discountPolicy;


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
