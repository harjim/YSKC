using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_rdEmployeeAttendanceHourAndEditCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "attendanceEdit",
                table: "p_rdEmployee",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<decimal>(
                name: "attendanceHour",
                table: "p_rdEmployee",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "attendanceEdit",
                table: "p_rdEmployee");

            migrationBuilder.DropColumn(
                name: "attendanceHour",
                table: "p_rdEmployee");
        }
    }
}
