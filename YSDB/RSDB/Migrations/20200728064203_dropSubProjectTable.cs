using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dropSubProjectTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_subproject");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_subproject",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    beginDate = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    deptIds = table.Column<string>(maxLength: 50, nullable: true),
                    deptName = table.Column<string>(maxLength: 100, nullable: true),
                    endDate = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 500, nullable: true),
                    subName = table.Column<string>(maxLength: 200, nullable: false),
                    year = table.Column<short>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_subproject", x => x.id);
                });
        }
    }
}
