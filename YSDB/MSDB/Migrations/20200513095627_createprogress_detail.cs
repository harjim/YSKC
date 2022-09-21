using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createprogress_detail : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "createTime",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "customerId",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "mType",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "memberId",
                table: "project_progress");

            migrationBuilder.DropColumn(
                name: "remark",
                table: "project_progress");

            migrationBuilder.RenameColumn(
                name: "status",
                table: "project_progress",
                newName: "userId");

            migrationBuilder.RenameColumn(
                name: "projectId",
                table: "project_progress",
                newName: "stage");

            migrationBuilder.RenameColumn(
                name: "productId",
                table: "project_progress",
                newName: "companyId");

            migrationBuilder.RenameColumn(
                name: "lastUpdateTime",
                table: "project_progress",
                newName: "operationTime");

            migrationBuilder.CreateTable(
                name: "project_progress_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    stage = table.Column<int>(nullable: false),
                    operationTime = table.Column<DateTime>(nullable: false),
                    result = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_progress_detail", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_progress_detail");

            migrationBuilder.RenameColumn(
                name: "userId",
                table: "project_progress",
                newName: "status");

            migrationBuilder.RenameColumn(
                name: "stage",
                table: "project_progress",
                newName: "projectId");

            migrationBuilder.RenameColumn(
                name: "operationTime",
                table: "project_progress",
                newName: "lastUpdateTime");

            migrationBuilder.RenameColumn(
                name: "companyId",
                table: "project_progress",
                newName: "productId");

            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "project_progress",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "project_progress",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "customerId",
                table: "project_progress",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "project_progress",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "mType",
                table: "project_progress",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "memberId",
                table: "project_progress",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "project_progress",
                maxLength: 200,
                nullable: true);
        }
    }
}
