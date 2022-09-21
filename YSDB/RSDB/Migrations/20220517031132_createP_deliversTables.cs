using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_deliversTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "roleType",
                table: "user",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "docFileType",
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
                    docFileId = table.Column<int>(nullable: false),
                    docType = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_docFileType", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_audit_opt",
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
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    ysTech = table.Column<string>(maxLength: 30, nullable: true),
                    ysFina = table.Column<string>(maxLength: 30, nullable: true),
                    ftyTech = table.Column<string>(maxLength: 30, nullable: true),
                    ftyFina = table.Column<string>(maxLength: 30, nullable: true),
                    areaTech = table.Column<string>(maxLength: 30, nullable: true),
                    areaFina = table.Column<string>(maxLength: 30, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_opt", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_deliver",
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
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    deliverType = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    node = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_deliver", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_deliver_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    deliverId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true),
                    node = table.Column<int>(nullable: false),
                    logTime = table.Column<int>(nullable: false),
                    auditUser = table.Column<string>(maxLength: 30, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_deliver_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "docFileType");

            migrationBuilder.DropTable(
                name: "p_audit_opt");

            migrationBuilder.DropTable(
                name: "p_deliver");

            migrationBuilder.DropTable(
                name: "p_deliver_log");

            migrationBuilder.DropColumn(
                name: "roleType",
                table: "user");
        }
    }
}
