package eci.server.ItemModule.entity.route;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoute is a Querydsl query type for Route
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoute extends EntityPathBase<Route> {

    private static final long serialVersionUID = 774779552L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoute route = new QRoute("route");

    public final eci.server.ItemModule.entity.entitycommon.QEntityDate _super = new eci.server.ItemModule.entity.entitycommon.QEntityDate(this);

    public final StringPath applicant_comment = createString("applicant_comment");

    public final eci.server.ItemModule.entity.member.QMember approver;

    public final StringPath approver_comment = createString("approver_comment");

    public final ListPath<Route, QRoute> children = this.<Route, QRoute>createList("children", Route.class, QRoute.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath inProgress = createBoolean("inProgress");

    public final eci.server.ItemModule.entity.item.QItem item;

    public final StringPath lifecycleStatus = createString("lifecycleStatus");

    public final eci.server.ItemModule.entity.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRoute parent;

    public final eci.server.ItemModule.entity.member.QMember reviewer;

    public final StringPath reviewer_comment = createString("reviewer_comment");

    public final NumberPath<Integer> revisedCnt = createNumber("revisedCnt", Integer.class);

    public final StringPath type = createString("type");

    public final StringPath workflow = createString("workflow");

    public final StringPath workflowPhase = createString("workflowPhase");

    public QRoute(String variable) {
        this(Route.class, forVariable(variable), INITS);
    }

    public QRoute(Path<? extends Route> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoute(PathMetadata metadata, PathInits inits) {
        this(Route.class, metadata, inits);
    }

    public QRoute(Class<? extends Route> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approver = inits.isInitialized("approver") ? new eci.server.ItemModule.entity.member.QMember(forProperty("approver"), inits.get("approver")) : null;
        this.item = inits.isInitialized("item") ? new eci.server.ItemModule.entity.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new eci.server.ItemModule.entity.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.parent = inits.isInitialized("parent") ? new QRoute(forProperty("parent"), inits.get("parent")) : null;
        this.reviewer = inits.isInitialized("reviewer") ? new eci.server.ItemModule.entity.member.QMember(forProperty("reviewer"), inits.get("reviewer")) : null;
    }

}

