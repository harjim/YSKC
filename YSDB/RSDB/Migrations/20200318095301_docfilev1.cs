using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class docfilev1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "docFile",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    docName = table.Column<string>(maxLength: 50, nullable: false),
                    stages = table.Column<string>(maxLength: 30, nullable: true),
                    seq = table.Column<int>(nullable: false),
                    templatePath = table.Column<string>(maxLength: 30, nullable: true),
                    required = table.Column<bool>(nullable: false),
                    multiple = table.Column<bool>(nullable: false),
                    enabled = table.Column<bool>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    msCreatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docFile", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "docFileItem",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    itemKey = table.Column<string>(maxLength: 20, nullable: false),
                    itemTitle = table.Column<string>(maxLength: 50, nullable: false),
                    itemMode = table.Column<int>(nullable: false),
                    itemType = table.Column<string>(nullable: true),
                    required = table.Column<bool>(nullable: false),
                    docFileId = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docFileItem", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "docFile");

            migrationBuilder.DropTable(
                name: "docFileItem");
        }
    }
}
