package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacture is a Querydsl query type for Manufacture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacture extends EntityPathBase<Manufacture> {

    private static final long serialVersionUID = 1215182698L;

    public static final QManufacture manufacture = new QManufacture("manufacture");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QManufacture(String variable) {
        super(Manufacture.class, forVariable(variable));
    }

    public QManufacture(Path<? extends Manufacture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacture(PathMetadata metadata) {
        super(Manufacture.class, metadata);
    }

}

