package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QColor is a Querydsl query type for Color
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColor extends EntityPathBase<Color> {

    private static final long serialVersionUID = -500013876L;

    public static final QColor color1 = new QColor("color1");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    public final StringPath code = createString("code");

    public final StringPath color = createString("color");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QColor(String variable) {
        super(Color.class, forVariable(variable));
    }

    public QColor(Path<? extends Color> path) {
        super(path.getType(), path.getMetadata());
    }

    public QColor(PathMetadata metadata) {
        super(Color.class, metadata);
    }

}

