package eci.server.ItemModule.entity.newRoute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRouteType is a Querydsl query type for RouteType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRouteType extends EntityPathBase<RouteType> {

    private static final long serialVersionUID = 1753863682L;

    public static final QRouteType routeType = new QRouteType("routeType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath module = createString("module");

    public final StringPath name = createString("name");

    public final StringPath todo = createString("todo");

    public QRouteType(String variable) {
        super(RouteType.class, forVariable(variable));
    }

    public QRouteType(Path<? extends RouteType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRouteType(PathMetadata metadata) {
        super(RouteType.class, metadata);
    }

}

