package eci.server.ItemModule.entity.newRoute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteProduct is a Querydsl query type for RouteProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouteProduct extends EntityPathBase<RouteProduct> {

    private static final long serialVersionUID = 1619505447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteProduct routeProduct = new QRouteProduct("routeProduct");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    public final StringPath comments = createString("comments");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath disabled = createBoolean("disabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RouteProductMember, QRouteProductMember> members = this.<RouteProductMember, QRouteProductMember>createList("members", RouteProductMember.class, QRouteProductMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRouteOrdering newRoute;

    public final NumberPath<Integer> origin_seq = createNumber("origin_seq", Integer.class);

    public final BooleanPath passed = createBoolean("passed");

    public final BooleanPath rejected = createBoolean("rejected");

    public final StringPath route_name = createString("route_name");

    public final BooleanPath route_show = createBoolean("route_show");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final QRouteType type;

    public QRouteProduct(String variable) {
        this(RouteProduct.class, forVariable(variable), INITS);
    }

    public QRouteProduct(Path<? extends RouteProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteProduct(PathMetadata metadata, PathInits inits) {
        this(RouteProduct.class, metadata, inits);
    }

    public QRouteProduct(Class<? extends RouteProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.newRoute = inits.isInitialized("newRoute") ? new QRouteOrdering(forProperty("newRoute"), inits.get("newRoute")) : null;
        this.type = inits.isInitialized("type") ? new QRouteType(forProperty("type")) : null;
    }

}

