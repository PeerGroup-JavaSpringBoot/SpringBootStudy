package eci.server.ItemModule.entity.newRoute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteProductMember is a Querydsl query type for RouteProductMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouteProductMember extends EntityPathBase<RouteProductMember> {

    private static final long serialVersionUID = 1719234209L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteProductMember routeProductMember = new QRouteProductMember("routeProductMember");

    public final eci.server.ItemModule.entity.member.QMember member;

    public final QRouteProduct routeProduct;

    public QRouteProductMember(String variable) {
        this(RouteProductMember.class, forVariable(variable), INITS);
    }

    public QRouteProductMember(Path<? extends RouteProductMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteProductMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteProductMember(PathMetadata metadata, PathInits inits) {
        this(RouteProductMember.class, metadata, inits);
    }

    public QRouteProductMember(Class<? extends RouteProductMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new eci.server.ItemModule.entity.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.routeProduct = inits.isInitialized("routeProduct") ? new QRouteProduct(forProperty("routeProduct"), inits.get("routeProduct")) : null;
    }

}

