package eci.server.ItemModule.entity.newRoute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteOrdering is a Querydsl query type for RouteOrdering
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouteOrdering extends EntityPathBase<RouteOrdering> {

    private static final long serialVersionUID = 903137436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteOrdering routeOrdering = new QRouteOrdering("routeOrdering");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final eci.server.ItemModule.entity.item.QItem item;

    public final StringPath lifecycleStatus = createString("lifecycleStatus");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> present = createNumber("present", Integer.class);

    public final NumberPath<Integer> revisedCnt = createNumber("revisedCnt", Integer.class);

    public final StringPath type = createString("type");

    public QRouteOrdering(String variable) {
        this(RouteOrdering.class, forVariable(variable), INITS);
    }

    public QRouteOrdering(Path<? extends RouteOrdering> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteOrdering(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteOrdering(PathMetadata metadata, PathInits inits) {
        this(RouteOrdering.class, metadata, inits);
    }

    public QRouteOrdering(Class<? extends RouteOrdering> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new eci.server.ItemModule.entity.item.QItem(forProperty("item"), inits.get("item")) : null;
    }

}

