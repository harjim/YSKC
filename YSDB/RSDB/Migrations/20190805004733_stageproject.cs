using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class stageproject : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "docProcess",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    processName = table.Column<string>(maxLength: 30, nullable: false),
                    type = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 100, nullable: true),
                    msCreatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastMsUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docProcess", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "docProcessTemplate",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    processId = table.Column<int>(nullable: false),
                    seq = table.Column<int>(nullable: false),
                    templateId = table.Column<int>(nullable: false),
                    optional = table.Column<bool>(nullable: false),
                    enabled = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docProcessTemplate", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_document",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    docNumber = table.Column<string>(maxLength: 10, nullable: false),
                    docName = table.Column<string>(maxLength: 30, nullable: false),
                    Content = table.Column<string>(type: "text", nullable: false),
                    stageId = table.Column<int>(nullable: false),
                    processId = table.Column<int>(nullable: false),
                    templateId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_document", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_stage",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    stageName = table.Column<string>(maxLength: 20, nullable: false),
                    beginDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_stage", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "docProcess");

            migrationBuilder.DropTable(
                name: "docProcessTemplate");

            migrationBuilder.DropTable(
                name: "p_document");

            migrationBuilder.DropTable(
                name: "p_stage");
        }
    }
}
