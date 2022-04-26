package eci.server.ItemModule.entity.newRoute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteProductMemberId is a Querydsl query type for RouteProductMemberId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRouteProductMemberId extends BeanPath<RouteProductMemberId> {

    private static final long serialVersionUID = -1378331748L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteProductMemberId routeProductMemberId = new QRouteProductMemberId("routeProductMemberId");

    public final eci.server.ItemModule.entity.member.QMember member;

    public final QRouteProduct routeProduct;

    public QRouteProductMemberId(String variable) {
        this(RouteProductMemberId.class, forVariable(variable), INITS);
    }

    public QRouteProductMemberId(Path<? extends RouteProductMemberId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteProductMemberId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteProductMemberId(PathMetadata metadata, PathInits inits) {
        this(RouteProductMemberId.class, metadata, inits);
    }

    public QRouteProductMemberId(Class<? extends RouteProductMemberId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new eci.server.ItemModule.entity.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.routeProduct = inits.isInitialized("routeProduct") ? new QRouteProduct(forProperty("routeProduct"), inits.get("routeProduct")) : null;
    }

}

