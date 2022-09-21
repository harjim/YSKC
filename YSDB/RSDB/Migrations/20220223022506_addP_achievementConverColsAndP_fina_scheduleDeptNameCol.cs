using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_achievementConverColsAndP_fina_scheduleDeptNameCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "p_fina_schedule",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "converResult",
                table: "p_achievement_file",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "converType",
                table: "p_achievement",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "description",
                table: "p_achievement",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "source",
                table: "p_achievement",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "type",
                table: "p_achievement",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "deptName",
                table: "p_fina_schedule");

            migrationBuilder.DropColumn(
                name: "converResult",
                table: "p_achievement_file");

            migrationBuilder.DropColumn(
                name: "converType",
                table: "p_achievement");

            migrationBuilder.DropColumn(
                name: "description",
                table: "p_achievement");

            migrationBuilder.DropColumn(
                name: "source",
                table: "p_achievement");

            migrationBuilder.DropColumn(
                name: "type",
                table: "p_achievement");
        }
    }
}
