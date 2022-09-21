using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class updateProjectAndAddAudit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "beginDate",
                table: "project_member");

            migrationBuilder.DropColumn(
                name: "endDate",
                table: "project_member");

            migrationBuilder.AddColumn<DateTime>(
                name: "fBeginDate",
                table: "project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "fEndDate",
                table: "project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "tBeginDate",
                table: "project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "tEndDate",
                table: "project",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.CreateTable(
                name: "project_audit",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    bAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    tDeptId = table.Column<int>(nullable: false),
                    fDeptId = table.Column<int>(nullable: false),
                    bAuditorId = table.Column<int>(nullable: false),
                    bAuditTime = table.Column<DateTime>(nullable: false),
                    tAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    tGroupId = table.Column<int>(nullable: false),
                    tAuditorId = table.Column<int>(nullable: false),
                    tAuditTime = table.Column<DateTime>(nullable: false),
                    fAuditOpinion = table.Column<string>(maxLength: 200, nullable: true),
                    fGroupId = table.Column<int>(nullable: false),
                    fAuditorId = table.Column<int>(nullable: false),
                    fAuditTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_audit", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_audit");

            migrationBuilder.DropColumn(
                name: "fBeginDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fEndDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tBeginDate",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tEndDate",
                table: "project");

            migrationBuilder.AddColumn<DateTime>(
                name: "beginDate",
                table: "project_member",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "endDate",
                table: "project_member",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }
    }
}
