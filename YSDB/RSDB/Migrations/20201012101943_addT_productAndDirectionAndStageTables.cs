using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_productAndDirectionAndStageTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "t_direction",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    direction = table.Column<string>(maxLength: 20, nullable: false),
                    expiryDate = table.Column<DateTime>(nullable: false),
                    productId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_direction", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_product",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    addressCode = table.Column<string>(maxLength: 30, nullable: true),
                    year = table.Column<int>(nullable: false),
                    pType = table.Column<int>(nullable: false),
                    pLevel = table.Column<int>(nullable: false),
                    noticeNo = table.Column<string>(maxLength: 50, nullable: true),
                    noticeUrl = table.Column<string>(maxLength: 200, nullable: true),
                    appGuideUrl = table.Column<string>(maxLength: 200, nullable: true),
                    publicItemUrl = table.Column<string>(maxLength: 200, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_product", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_product_stage",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    stageKey = table.Column<string>(maxLength: 10, nullable: false),
                    productId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_product_stage", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_product_stage_list",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    stageId = table.Column<int>(nullable: false),
                    productId = table.Column<int>(nullable: false),
                    itemName = table.Column<string>(maxLength: 20, nullable: false),
                    itemType = table.Column<int>(nullable: false),
                    pattern = table.Column<string>(nullable: true),
                    remark = table.Column<string>(nullable: true),
                    required = table.Column<bool>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_product_stage_list", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_project_list",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    filePath = table.Column<string>(maxLength: 300, nullable: true),
                    stageListId = table.Column<int>(nullable: false),
                    fileName = table.Column<string>(maxLength: 100, nullable: true),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_project_list", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_direction");

            migrationBuilder.DropTable(
                name: "t_product");

            migrationBuilder.DropTable(
                name: "t_product_stage");

            migrationBuilder.DropTable(
                name: "t_product_stage_list");

            migrationBuilder.DropTable(
                name: "t_project_list");
        }
    }
}
