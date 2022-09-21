using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class doclist_new : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "listId",
                table: "sys_document",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "sys_docList",
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
                    listType = table.Column<int>(nullable: false),
                    docName = table.Column<string>(maxLength: 100, nullable: false),
                    desciption = table.Column<string>(maxLength: 200, nullable: true),
                    optional = table.Column<bool>(nullable: false),
                    seq = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_docList", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "sys_docList");

            migrationBuilder.DropColumn(
                name: "listId",
                table: "sys_document");
        }
    }
}
